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
package net.mikc.tools.clusterssh.events;

import java.awt.event.ActionEvent;

/**
 *
 * @author mikc
 */
public final class InputWindowEvent {

    private final String text;
    private final ActionEvent event;

    public InputWindowEvent(final String text, final ActionEvent event) {
        this.text = text;
        this.event = event;
    }

    public String getText() {
        return text;
    }

    public ActionEvent getEvent() {
        return event;
    }

}
