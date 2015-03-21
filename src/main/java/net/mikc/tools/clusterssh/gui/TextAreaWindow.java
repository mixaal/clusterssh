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

import net.mikc.tools.clusterssh.gui.domain.Appearance;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author mikc
 */
class TextAreaWindow extends JFrame {

    protected final JTextArea textArea;

    public TextAreaWindow(
            final String title,
            final Dimension dimension,
            final boolean editable,
            final boolean resizeable) {
        super(title);
        final int width = dimension.width;
        final int height = dimension.height;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(resizeable);

        this.setSize(width, height);

        textArea = new JTextArea();
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width, height));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(editable);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.setLayout(new FlowLayout());
        this.add(scrollPane, SwingConstants.CENTER);

        this.setVisible(true);
        this.setLayout(new FlowLayout());
        this.add(scrollPane);

    }

    void setAppearance(final Appearance appearance) {
        textArea.setFont(appearance.getFont());
        textArea.setBackground(appearance.getBackground());
        textArea.setForeground(appearance.getForeground());
    }

}
