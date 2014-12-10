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
package net.mikc.tools.clusterssh.gui.dialogs;

import javax.swing.JOptionPane;

/**
 *
 * @author mikc
 */
public class PromptYesNo {

    private final String message;
    private int answer;

    public PromptYesNo(final String message) {
        this.message = message;

    }

    public PromptYesNo display() {
        answer = JOptionPane.showOptionDialog(null, message, "action", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        return this;
    }

    public boolean getValue() {
        return answer == 0;
    }

}
