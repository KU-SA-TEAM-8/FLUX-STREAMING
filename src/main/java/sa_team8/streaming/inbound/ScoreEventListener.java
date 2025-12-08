package sa_team8.streaming.inbound;

import sa_team8.streaming.inbound.event.CompetitionDataChangeEvent;
import sa_team8.streaming.inbound.event.ScoreUpdateEvent;

public interface ScoreEventListener {
   void onCompetitionDataChange(CompetitionDataChangeEvent event);
   void onScoreUpdate(ScoreUpdateEvent event);
}
