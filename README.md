#jthumb

## Prerequisite

- [FFmpeg](https://www.ffmpeg.org/) - ffmpeg
- [FFmpeg](https://www.ffmpeg.org/) - ffprobe
- [ImageMagick](https://imagemagick.org/) - convert
- [ImageMagick](https://imagemagick.org/) - montage

## Testing

Sample video: https://sample-videos.com/video123/mkv/720/big_buck_bunny_720p_1mb.mkv

## TODO

mvn clean package && docker build -t jthumb -f ./.ci/Dockerfile .

wget -O ~/tmp/test.mp4 https://sample-videos.com/video123/mkv/720/big_buck_bunny_720p_1mb.mkv
docker run -v ~/tmp:/app/work jthumb /app/work/test.mp4