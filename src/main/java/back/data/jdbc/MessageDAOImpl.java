package back.data.jdbc;

import back.data.MessageDAO;
import back.model.Message;

import java.util.List;

public class MessageDAOImpl implements MessageDAO {

    private final DataAccess dataAccess;

    public MessageDAOImpl(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }


    @Override
    public List<Message> getSentMessages(long userId){
        return dataAccess.getSentMessages(userId);
    }

    @Override
    public List<Message> getReceivedMessages(long userId){
        return dataAccess.getReceivedMessages(userId);
    }

    @Override
    public void storeMessage(Message message){
        dataAccess.storeMessage(message);
    }

    @Override
    public Message getMessage(int messageId){
        return dataAccess.getMessage(messageId);
    }

    @Override
    public void deleteMessage(int messageId){
        dataAccess.deleteMessage(messageId);
    }
}
