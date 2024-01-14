package univcapstone.employmentsite.repository;

import univcapstone.employmentsite.domain.Reply;

public interface ReplyRepository {
    Reply addReply(Reply reply);
    int deleteReply();
}
