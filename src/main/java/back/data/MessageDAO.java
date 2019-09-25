package back.data;

import back.model.Message;

import java.util.List;

public interface MessageDAO {
    List<Message> getSentMessages(long userId);

    List<Message> getReceivedMessages(long userId);

    void storeMessage(Message message);

    Message getMessage(int messageId);

    void deleteMessage(int messageId);

    int getUnreadMessagesAmount(long id);
}
