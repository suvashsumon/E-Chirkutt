package com.suvash.chirkutt.Service;

import com.suvash.chirkutt.Dto.Request.MessageDto;
import com.suvash.chirkutt.Model.Message;

public interface MessageService {
    Message storeMessage(MessageDto messageDto);
}
