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
package net.mikc.tools.clusterssh.controller.modules;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import net.mikc.tools.clusterssh.config.Config;
import net.mikc.tools.clusterssh.controller.connection.RemoteConnection;
import net.mikc.tools.clusterssh.controller.connection.RemoteConnectionFactory;
import net.mikc.tools.clusterssh.controller.connection.impl.RemoteTerminalConnection;
import net.mikc.tools.clusterssh.controller.providers.MessageBusProvider;
import net.mikc.tools.clusterssh.gui.user.InputWindow;
import net.mikc.tools.clusterssh.gui.user.InputWindowFactory;
import net.mikc.tools.clusterssh.gui.user.TerminalWindowFactory;
import net.mikc.tools.clusterssh.gui.user.impl.FakeInputWindow;
import net.mikc.tools.clusterssh.gui.user.impl.SwingInputWindow;
import net.mikc.tools.clusterssh.gui.user.impl.TerminalWindow;
import net.mikc.tools.clusterssh.gui.window.Window;
import net.mikc.tools.clusterssh.gui.window.WindowFactory;
import net.mikc.tools.clusterssh.gui.window.impl.TextAreaWindow;
import net.mikc.tools.clusterssh.gui.window.impl.UnitTestWindow;
import net.mikc.tools.clusterssh.transport.RemoteSession;
import net.mikc.tools.clusterssh.transport.channel.Channel;
import net.mikc.tools.clusterssh.transport.channel.ChannelFactory;
import net.mikc.tools.clusterssh.transport.channel.impl.FakeSsh;
import net.mikc.tools.clusterssh.transport.channel.impl.JschSsh;
import net.mikc.tools.clusterssh.transport.pump.Sender;
import net.mikc.tools.clusterssh.transport.pump.impl.ChannelSender;

import java.util.Collection;


public class TestUnitApplicationModule extends AbstractModule {
    private static final ImmutableCollection<RemoteSession> sessions = ImmutableSet.of(
            new RemoteSession("mikc", null, "localhost"),
            new RemoteSession("root", null, "localhost")
    );


    @Override
    protected void configure() {
        bind(new TypeLiteral<Collection<RemoteSession>>() {
        }).toInstance(sessions);
        bind(EventBus.class).toProvider(MessageBusProvider.class);
        bind(Sender.class).to(ChannelSender.class);
        install(
                new FactoryModuleBuilder().implement(Channel.class, FakeSsh.class).build(ChannelFactory.class)
        );
        install(
                new FactoryModuleBuilder().implement(RemoteConnection.class, RemoteTerminalConnection.class).build(RemoteConnectionFactory.class)
        );
        install(
                new FactoryModuleBuilder().implement(Window.class, UnitTestWindow.class).build(WindowFactory.class)
        );
        install(
                new FactoryModuleBuilder().implement(InputWindow.class, FakeInputWindow.class).build(InputWindowFactory.class)
        );
        install(
                new FactoryModuleBuilder().implement(TerminalWindow.class, TerminalWindow.class).build(TerminalWindowFactory.class)
        );
    }
}
