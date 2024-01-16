package univcapstone.employmentsite.domain;

import java.util.Date;

public class Reply {
    private Long reply_id;
    private Long ref_id;
    private String reply_cotent;
    private Date date;
    private Long post_id;

    public Long getReply_id() {
        return reply_id;
    }

    public void setReply_id(Long reply_id) {
        this.reply_id = reply_id;
    }

    public Long getRef_id() {
        return ref_id;
    }

    public void setRef_id(Long ref_id) {
        this.ref_id = ref_id;
    }

    public String getReply_cotent() {
        return reply_cotent;
    }

    public void setReply_cotent(String reply_cotent) {
        this.reply_cotent = reply_cotent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPost_id() {
        return post_id;
    }

    public void setPost_id(Long post_id) {
        this.post_id = post_id;
    }
}
