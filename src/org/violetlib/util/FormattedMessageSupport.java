/*
 * Copyright (c) 2021 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.util;

import org.jetbrains.annotations.*;
import org.violetlib.annotations.NoInstances;

/**

*/

public final @NoInstances class FormattedMessageSupport
{
    private FormattedMessageSupport()
    {
        throw new AssertionError("FormattedMessageSupport may not be instantiated");
    }

    /**
      This convenience method is intended primarily for creating HTML error messages that refer to source text. In the
      most common case, the error message is constructed using a plain text message and some number of plain text source
      text segments that should appear &ldquo;quoted&rdquo; in the HTML result.
      <p>
      The basic message <code>msg</code> is assumed to be plain text. It is copied directly (if the selected format is
      plain text) or encoded into HTML (if the selected format is HTML), except that like printf, certain markers in
      <code>msg</code> are replaced (typically by the next element of <code>args</code>, formatted in some way as
      directed by the marker). The arguments may be of any type. If an argument is not a String, it is first converted
      to a String using the {@code toString} method.
      <p>
      The marker <code>&quot;%q&quot;</code> takes the next element of <code>args</code> to be plain text and formats it
      to appear as quoted text in the message. If the selected format is HTML, the text is encoded and wrapped in an
      HTML span with class <code>q</code>. Otherwise, the text is enclosed in quotation marks.
      <p>
      The marker <code>&quot;%s&quot;</code> copies the next element of <code>args</code> directly to the message. It is
      assumed to be in the proper format already.
      <p>
      To include a percent sign in the message, use <code>&quot;%%&quot;</code>.

      @param useHTML If true, the returned message is in HTML. Otherwise, the returned message is plain text.
      @param msg The basic plain text message.
      @param args Each element of this array is converted as needed to a String using the {@code toString} method and
      included in the returned message as described above, replacing the corresponding marker.

      @return the constructed message.
    */

    public static @NotNull String constructMessage(boolean useHTML, @NotNull String msg, @NotNull Object[] args)
    {
        StringBuilder sb = new StringBuilder();
        int nextArg = 0;
        int mlen = msg.length();
        boolean leadingSpace = true;

        for (int i = 0; i < mlen; ) {
            char ch = msg.charAt(i++);
            if (ch == '%') {
                leadingSpace = false;
                char ch2 = msg.charAt(i++);
                if (ch2 == 'q') {
                    String quote = toString(args[nextArg++]);
                    if (useHTML) {
                        sb.append("&laquo;<span class=\"q\">");
                        sb.append(encodeAsHTML(quote));
                        sb.append("</span>&raquo;");
                    } else {
                        sb.append("\"");
                        sb.append(quote);
                        sb.append("\"");
                    }
                } else if (ch2 == '%') {
                    sb.append('%');
                } else if (ch2 == 's') {
                    String quote = toString(args[nextArg++]);
                    sb.append(quote);
                } else {
                    // unrecognized format
                }
            } else if (ch == ' ' && leadingSpace) {
                if (useHTML) {
                    sb.append("&nbsp;");
                } else {
                    sb.append(ch);
                }
            } else {
                leadingSpace = false;
                if (useHTML) {
                    sb.append(encodeAsHTML(ch));
                } else {
                    sb.append(ch);
                }
            }
        }

        String s = sb.toString();
        return s;
    }

    private static @NotNull String toString(@NotNull Object o)
    {
        String s = Extensions.getExtension(o, String.class);
        if (s != null) {
            return s;
        }
        s = o.toString();
        return s != null ? s : "";
    }

    private static @NotNull String encodeAsHTML(@NotNull String s)
    {
        if (s.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int len = s.length();

        boolean leadingSpace = true;

        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);

            if (leadingSpace) {
                if (ch == ' ') {
                    sb.append("&nbsp;");
                    continue;
                }
                leadingSpace = false;
            }

            String rs = encodeAsHTML(ch);
            sb.append(rs);
        }

        return sb.toString();
    }

    private static @NotNull String encodeAsHTML(char ch)
    {
        int code = (int) ch;

        if (isHTMLMetaCharacter(ch)) {
            return "&#" + code + ";";
        }

        if (ch == '\n' || ch == '\r') {
            return "\n";
        }

        if (code < 32 || code >= 128) {
            return "&#" + code + ";";
        }

        return "" + ch;
    }

    private static boolean isHTMLMetaCharacter(char ch)
    {
        return ch == '<' || ch == '>' || ch == '&' || ch == '"';
    }
}
