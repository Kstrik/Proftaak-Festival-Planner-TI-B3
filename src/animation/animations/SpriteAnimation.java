package animation.animations;

import animation.statelogic.SpriteAnimationState;

import java.awt.image.BufferedImage;

public class SpriteAnimation
{
    private float duration;
    private float animationTime;
    private BufferedImage[] images;
    private int imageIndex;
    private boolean isPlaying;
    private boolean isLooping;

    private SpriteAnimationState animationState;

    public SpriteAnimation(float durationInSeconds, BufferedImage image, int tilesX, int tilesY, int beginTile, int endTile, boolean isLooping)
    {
        this.duration = durationInSeconds;
        this.animationTime = 0.0f;
        this.imageIndex = 0;
        this.isLooping = isLooping;
        this.isPlaying = false;

        this.splitImage(image, tilesX, tilesY, beginTile, endTile);
    }

    public void attachAnimationState(SpriteAnimationState animationState)
    {
        if(animationState != null)
        {
            this.animationState = animationState;
        }
    }

    private void splitImage(BufferedImage bufferedImage, int tilesX, int tilesY, int beginTile, int endTile)
    {
        int tileWidth = bufferedImage.getWidth() / tilesX;
        int tileHeight = bufferedImage.getHeight() / tilesY;

        beginTile = (beginTile < 0) ? 0 : beginTile;
        endTile = (endTile > tilesX * tilesY) ? tilesX * tilesY : endTile;

        this.images = new BufferedImage[endTile - beginTile];

        for(int i = 0; i < endTile; i++)
        {
            if(i >= beginTile)
            {
                this.images[i - beginTile] = bufferedImage.getSubimage(tileWidth * (i%tilesX), (tileHeight * (i/tilesX)), tileWidth, tileHeight);
            }
        }
    }

    public void update(double deltaTime)
    {
        if(this.isPlaying)
        {
            this.animationTime += deltaTime;

            if(this.animationTime >= (this.duration / this.images.length))
            {
                updateCounters();
            }
        }
    }

    private void updateCounters()
    {
        if(this.imageIndex + 1 == this.images.length)
        {
            this.imageIndex = 0;

            this.isPlaying = (this.isLooping) ? true : false;

            if(!this.isPlaying)
            {
                if(this.animationState != null)
                {
                    this.animationState.onAnimationEnd();
                }
            }
        }
        else
        {
            this.imageIndex++;
        }

        this.animationTime = 0.0f;
    }

    public void play()
    {
        this.isPlaying = true;

        if(this.animationState != null)
        {
            this.animationState.onAnimationStart();
        }
    }

    public void stop()
    {
        this.isPlaying = false;
        this.imageIndex = 0;
        this.animationTime = 0.0f;

        if(this.animationState != null)
        {
            this.animationState.onAnimationStopped();
        }
    }

    public BufferedImage getNextFrame()
    {
        if(this.images.length != 0)
        {
            return this.images[this.imageIndex];
        }

        return null;
    }
}
