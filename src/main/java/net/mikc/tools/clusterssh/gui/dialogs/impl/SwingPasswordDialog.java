/*
 *   clusterssh - run ssh clients for on multiple sites.
 *
 *   Copyright (C) 2014  Michal Conos
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this program; if not, write to the Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package net.mikc.tools.clusterssh.gui.dialogs.impl;


import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.mikc.tools.clusterssh.gui.dialogs.PasswordDialog;

import javax.swing.*;

public class SwingPasswordDialog implements PasswordDialog {
    private final String message;
    private int answer;
    private String password = null;

    @Inject
    SwingPasswordDialog(@Assisted final String message) {
        this.message = message;
    }

    @Override
    public PasswordDialog display() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter a password:");
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"OK", "Cancel"};
        answer = JOptionPane.showOptionDialog(null, panel, message,
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (0 == answer) {
            this.password = new String(pass.getPassword());
        }
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean hasPassword() {
        return null != password;
    }
}
