/*
 * Copyright (C) 2014 Michal Conos
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

package net.mikc.tools.clusterssh.gui.window.impl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.mikc.tools.clusterssh.gui.domain.Appearance;
import net.mikc.tools.clusterssh.gui.window.Window;
import net.mikc.tools.clusterssh.gui.window.WindowOptions;

import javax.swing.*;
import java.awt.*;

public class UnitTestWindow implements Window {
    private String text;
    private final String title;
    private final Dimension dimension;
    private final WindowOptions options;

    @Inject
    UnitTestWindow(
            @Assisted final String title,
            @Assisted final Dimension dimension,
            @Assisted final WindowOptions options
    ) {
        this.title = title;
        this.dimension = dimension;
        this.options = options;
        this.text = "";
    }


    @Override
    public void setAppearance(Appearance appearance) {

    }

    @Override
    public InputMap getInputMap(int condition) {
        return null;
    }

    @Override
    public ActionMap getActionMap() {
        return null;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void appendText(String text) {
        this.text += text;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
