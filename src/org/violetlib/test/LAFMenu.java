/*
 * Copyright (c) 2023 Alan Snyder.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the license agreement. For details see
 * accompanying license terms.
 */

package org.violetlib.test;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import org.jetbrains.annotations.*;

/**
  A menu that allows the user to select and install a known {@link javax.swing.LookAndFeel}.
*/

public final class LAFMenu
  extends JMenu
{
    /**
      Create a look and feel menu.
    */

    public static @NotNull JMenu create(@Nullable Runnable updateResponder)
    {
        return new LAFMenu(updateResponder);
    }

    private final @Nullable Runnable updateResponder;

    private LAFMenu(@Nullable Runnable updateResponder)
    {
        super("LAF");

        this.updateResponder = updateResponder;

        ButtonGroup group = new ButtonGroup();

        try {
            // VAqua may be the current LAF without having been installed
            Class<?> c = Class.forName("org.violetlib.aqua.AquaLookAndFeel");
            LookAndFeel laf = null;
            LookAndFeel currentLAF = UIManager.getLookAndFeel();
            if (currentLAF.getClass() == c) {
                laf = currentLAF;
            }
            addToMenu("VAqua", "org.violetlib.aqua.AquaLookAndFeel", laf, group);
        } catch (ClassNotFoundException ignore) {
        }

        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            String name = info.getName();
            String className = info.getClassName();
            if (!className.equals("org.violetlib.aqua.AquaLookAndFeel")) {
                addToMenu(name, className, null, group);
            }
        }
    }

    private void addToMenu(String name, String className, LookAndFeel laf, ButtonGroup group) {
        JRadioButtonMenuItem mi = new JRadioButtonMenuItem(name);
        group.add(mi);
        if (UIManager.getLookAndFeel().getClass().toString().equals(className)) {
            mi.setSelected(true);
        }
        add(mi);

        mi.addActionListener(e -> {
            if (installLAF(className, laf)) {
                mi.setSelected(true);
            } else {
                mi.setEnabled(false);
            }
        });
    }

    private boolean installLAF(String className, LookAndFeel laf)
    {
        try {
            if (laf != null) {
                UIManager.setLookAndFeel(laf);
            } else {
                UIManager.setLookAndFeel(className);
            }
            updatedLAF();
            return true;

        } catch (Throwable ex) {
            System.err.println("Unable to install " + className + ": " + ex.getMessage());
            return false;
        }
    }

    private void updatedLAF()
    {
        if (updateResponder != null) {
            updateResponder.run();
        }
    }
}
