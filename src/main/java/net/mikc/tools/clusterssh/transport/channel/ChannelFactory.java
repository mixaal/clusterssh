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
package net.mikc.tools.clusterssh.transport.channel;

import com.google.common.eventbus.EventBus;
import net.mikc.tools.clusterssh.transport.RemoteSession;
import net.mikc.tools.clusterssh.transport.channel.impl.JschSsh;

/**
 *
 * @author mikc
 */
public final class ChannelFactory {

    public static Channel newChannel(final RemoteSession session, final EventBus bus) {
        return new JschSsh(session, bus);
    }
}
