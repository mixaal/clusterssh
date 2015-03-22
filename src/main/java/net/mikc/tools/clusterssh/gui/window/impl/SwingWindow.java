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
package net.mikc.tools.clusterssh.gui.window.impl;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.mikc.tools.clusterssh.gui.window.Window;
import net.mikc.tools.clusterssh.gui.domain.Appearance;
import net.mikc.tools.clusterssh.gui.window.WindowOptions;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author mikc
 */
public class SwingWindow extends JFrame implements Window {

    private final JTextArea textArea;

    @AssistedInject
    SwingWindow(
            @Assisted final String title,
            @Assisted final Dimension dimension,
            @Assisted final WindowOptions options
    ) {
        super(title);
        final int width = dimension.width;
        final int height = dimension.height;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(options.getResizable());

        this.setSize(width, height);

        textArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width, height));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(options.getEditable());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setLayout(new FlowLayout());
        this.add(scrollPane, SwingConstants.CENTER);

        this.setVisible(true);
        this.setLayout(new FlowLayout());
        this.add(scrollPane);
    }

    @Override
    public InputMap getInputMap(int condition) {
        return textArea.getInputMap(condition);
    }

    @Override
    public ActionMap getActionMap() {
        return textArea.getActionMap();
    }

    @Override
    public void setText(String text) {
        textArea.setText(text);
    }

    @Override
    public String getText() {
        return textArea.getText();
    }

    @Override
    public void appendText(String text) {
        textArea.append(text);
    }

    @Override
    public void setAppearance(final Appearance appearance) {
        textArea.setFont(appearance.getFont());
        textArea.setBackground(appearance.getBackground());
        textArea.setForeground(appearance.getForeground());
    }

}
