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

import com.google.common.eventbus.Subscribe;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.events.OutputTerminalDataEvent;
import net.mikc.tools.clusterssh.gui.domain.Appearance;

import java.awt.*;

/**
 *
 * @author mikc
 */
public final class TerminalWindow extends TextAreaWindow {

    private final String title;
    private final String host, user;
    private final Dimension dimension;

    private String userMark() {
        return (isRoot()) ? " # " : " $ ";
    }

    boolean isRoot() {
        return "root".equals(user);
    }

    private Appearance getAppearance() {
        return (isRoot())
                ? Config.LookAndFeel.ROOT_APPEARANCE
                : Config.LookAndFeel.USER_APPEARANCE;
    }

    public TerminalWindow(final String host, final String user, final Dimension dimension) {
        super(user + "@" + host, dimension, false, false);
        this.title = getTitle();
        this.user = user;
        this.host = host;
        this.dimension = dimension;
        setAppearance(getAppearance());
    }

    public void appendText(final String text) {
        textArea.append(text);
    }

    public int getTerminalWidth() {
        return this.dimension.width;
    }

    public int getTerminalHeight() {
        return this.dimension.height;
    }

    public String getTerminalTitle() {
        return this.title;
    }

    @Subscribe
    public <T extends Object> void onDataArrival(OutputTerminalDataEvent<T> evt) {
        final T payload = evt.getPayload();
        if (payload instanceof String) {
            final String output = (String) payload;
            appendText(output);
        }
    }
}
