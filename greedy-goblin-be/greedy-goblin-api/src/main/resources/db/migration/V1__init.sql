CREATE TABLE games (
                       game_id UUID PRIMARY KEY,
                       scene_id UUID,
                       state VARCHAR(50) NOT NULL,
                       players JSONB NOT NULL
);

CREATE TABLE scenes (
                        scene_id UUID PRIMARY KEY,
                        data JSONB NOT NULL
);

CREATE TABLE player_actions (
                                id SERIAL PRIMARY KEY,
                                game_id UUID NOT NULL,
                                player_id UUID NOT NULL,
                                action_id VARCHAR(50) NOT NULL,
                                scene_id UUID NOT NULL,
                                timestamp TIMESTAMP NOT NULL DEFAULT NOW()
);
