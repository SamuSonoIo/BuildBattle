/*
 *
 * BuildBattle - Ultimate building competition minigame
 * Copyright (C) 2021 Plugily Projects - maintained by Tigerpanzer_02, 2Wild4You and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package plugily.projects.buildbattle.arena.managers;

import plugily.projects.buildbattle.arena.BaseArena;
import plugily.projects.buildbattle.arena.GuessArena;
import plugily.projects.minigamesbox.classic.arena.ArenaState;
import plugily.projects.minigamesbox.classic.arena.PluginArena;
import plugily.projects.minigamesbox.classic.arena.managers.PluginScoreboardManager;
import plugily.projects.minigamesbox.classic.handlers.language.MessageBuilder;
import plugily.projects.minigamesbox.classic.user.User;
import plugily.projects.minigamesbox.classic.utils.scoreboard.common.EntryBuilder;
import plugily.projects.minigamesbox.classic.utils.scoreboard.type.Entry;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tigerpanzer_02
 * <p>Created at 19.12.2021
 */
public class ScoreboardManager extends PluginScoreboardManager {

  private final PluginArena arena;
  private final List<String> cachedBaseFormat = new ArrayList<>();

  public ScoreboardManager(PluginArena arena) {
    super(arena);
    this.arena = arena;
  }

  @Override
  public List<Entry> formatScoreboard(User user) {
    EntryBuilder builder = new EntryBuilder();
    List<String> lines;
    if(user.getArena().getArenaState() == ArenaState.FULL_GAME) {
      lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content.Starting");
    } else if(user.getArena().getArenaState() == ArenaState.IN_GAME) {
      if(user.getArena() instanceof GuessArena) {
        lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content." + user.getArena().getArenaState().getFormattedName() + ".Guess-The-Build" + (((GuessArena) user.getArena()).getArenaInGameStage() == BaseArena.ArenaInGameStage.PLOT_VOTING ? "-Waiting" : ""));
      } else {
        if(user.getArena().getArenaOption("PLOT_MEMBER_SIZE") <= 1) {
          lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content." + user.getArena().getArenaState().getFormattedName() + ".Classic");
        } else {
          lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content." + user.getArena().getArenaState().getFormattedName() + ".Teams");
        }
      }
    } else if(user.getArena().getArenaState() == ArenaState.ENDING) {
      if(user.getArena() instanceof GuessArena) {
        lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content." + user.getArena().getArenaState().getFormattedName() + ".Guess-The-Build");
      } else {
        lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content." + user.getArena().getArenaState().getFormattedName() + ".Classic");
      }
    } else {
      lines = user.getArena().getPlugin().getLanguageManager().getLanguageList("Scoreboard.Content." + user.getArena().getArenaState().getFormattedName());
    }
    for(String line : lines) {
      builder.next(new MessageBuilder(line).player(user.getPlayer()).arena(arena).build());
    }
    return builder.build();
  }

}
