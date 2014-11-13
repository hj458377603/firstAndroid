package com.potato.gamevideo.entity.video.lol.duowan;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoSummary {
    public String amount_play ;

    public String auditingFlag ;

    public String channelId ;

    public String cover_url ;

    public String editorId ;

    public String introduction ;

    public String letv_video_id ;

    public String letv_video_unique ;

    public String notes ;

    public String title;

    public String totalPage ;

    private String upload_time;

    private String video_length ;

	@SuppressLint("SimpleDateFormat")
	public String getUpload_time() {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(upload_time);
			long uploadTime=date.getTime();
			long currentTime=new Date().getTime();
			long timeSpan=(currentTime-uploadTime)/1000;
			
			if(timeSpan<60){
				return "�ո�";
			}else if(timeSpan<3600&&timeSpan>=60){
				return timeSpan/60+"����ǰ";
			}
		} catch (ParseException e) {
			return upload_time;
		}
		
		return upload_time.substring(5);
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}

	public String getVideo_length() {
		int seconds = Integer.valueOf(video_length);

        if (seconds > 3600)
        {
            int hours = (seconds / 3600);
            int mins = seconds % 3600 / 60;
            return hours + "ʱ" + mins + "��";
        }
        else
        {
            int mins = (seconds / 60);
            int secs = seconds % 60;
            return mins + "��" + secs + "��";
        }
	}

	public void setVideo_length(String video_length) {
		this.video_length = video_length;
	}
    
    
}
