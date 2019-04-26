package ca.ulaval.glo4002.trading.rest.transaction;

import ca.ulaval.glo4002.trading.application.transaction.TransactionDTO;
import ca.ulaval.glo4002.trading.rest.transaction.views.requests.TransactionRequest;
import ca.ulaval.glo4002.trading.rest.transaction.views.responses.TransactionResponse;

public class TransactionViewAssembler {

    public TransactionDTO from(TransactionRequest transactionRequest) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setType(transactionRequest.getType());
        transactionDTO.setDate(transactionRequest.getDate());
        transactionDTO.setStockId(transactionRequest.getStock());
        transactionDTO.setReferencedTransactionNumber(transactionRequest.getTransactionNumber());
        transactionDTO.setQuantity(transactionRequest.getQuantity());
        return transactionDTO;
    }

    public TransactionResponse from(TransactionDTO transactionDTO) {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setTransactionNumber(transactionDTO.getTransactionNumber());
        transactionResponse.setType(transactionDTO.getType());
        transactionResponse.setDate(transactionDTO.getDate());
        transactionResponse.setFees(transactionDTO.getFees());
        transactionResponse.setStock(transactionDTO.getStockId());
        transactionResponse.setQuantity(transactionDTO.getQuantity());
        transactionResponse.setPrice(transactionDTO.getPrice());
        return transactionResponse;
    }

}
