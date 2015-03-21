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
package net.mikc.tools.clusterssh.gui;

import com.google.common.eventbus.EventBus;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.events.InputWindowEvent;

/**
 *
 * @author mikc
 */
public class InputWindow extends TextAreaWindow {

    private static final String PROMPT_TITLE = "Input Prompt";

    private final EventBus messageBus;

    public InputWindow(final EventBus messageBus) {
        super(
                PROMPT_TITLE,
                Config.LookAndFeel.INPUT_WINDOW_SIZE,
                true,
                false
        );
        this.messageBus = messageBus;

        setAppearance(Config.LookAndFeel.INPUT_APPEARANCE);
        InputMap inputMap = textArea.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap actionMap = textArea.getActionMap();

        // the key stroke we want to capture
        KeyStroke enterStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        KeyStroke altEnter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.CTRL_DOWN_MASK);

        // tell input map that we are handling the enter key and ctrl+enter key
        inputMap.put(enterStroke, enterStroke.toString());
        inputMap.put(altEnter, altEnter.toString());

        // tell action map just how we want to handle the enter key
        actionMap.put(enterStroke.toString(), new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final String userText = textArea.getText();
                textArea.setText("");
                messageBus.post(new InputWindowEvent(userText, e));
            }

        });

        // handle ctrl+enter key
        actionMap.put(altEnter.toString(), new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.append(" \\\n");
            }
        });

    }
}
