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
package net.mikc.tools.clusterssh.gui.domain;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author mikc
 */
public final class Appearance {

    final Color foreground, background;
    final Font font;

    public Appearance() {
        this(Color.blue, Color.white);
    }

    public Appearance(Color foreground, Color background) {
        this(foreground, background, new Font("Verdana", Font.BOLD, 12));
    }

    public Appearance(Color foreground, Color background, Font font) {
        this.foreground = foreground;
        this.background = background;
        this.font = font;
    }

    public Color getForeground() {
        return foreground;
    }

    public Color getBackground() {
        return background;
    }

    public Font getFont() {
        return font;
    }

}
