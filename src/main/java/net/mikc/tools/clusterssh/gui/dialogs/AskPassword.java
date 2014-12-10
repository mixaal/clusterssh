/*
 * Copyright (C) 2014 mikc
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.mikc.tools.clusterssh.gui.dialogs;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author mikc
 */
public class AskPassword {

    private final String message;
    private int answer;
    private String password = null;

    public AskPassword(final String message) {
        this.message = message;

    }

    public AskPassword display() {

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

    public String getPassword() {
        return password;
    }

    public boolean hasPassword() {
        return null != password;
    }

}
