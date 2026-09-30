package com.nttdata.service;

import com.nttdata.model.ExchangeRate;
import com.nttdata.model.dto.AccountResponseDto;
import com.nttdata.model.dto.CardDto;
import com.nttdata.model.dto.CardResponseDto;
import com.nttdata.model.dto.ExchangeRateDto;
import com.nttdata.repository.AccountRepository;
import com.nttdata.repository.CardRepository;
import com.nttdata.repository.ExchangeRateRepository;
import com.nttdata.util.Functions;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CardService {

    private final CardRepository cardRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public CardService(CardRepository cardRepository, ExchangeRateRepository exchangeRateRepository) {
        this.cardRepository = cardRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public Uni<List<CardResponseDto>> listCards(String documentNumber) {
        return exchangeRateRepository.getLastExchangeRate()
                .onItem().transformToUni(exchangeRateOpt ->
                        cardRepository.listCards(documentNumber)
                                .onItem().transform(cardList -> {
                                    List<CardResponseDto> cardResponseList = new ArrayList<>();

                                    for (CardDto card : cardList) {
                                        Double usedBalance;

                                        if (exchangeRateOpt.isPresent()) {
                                            ExchangeRateDto exchangeRate = exchangeRateOpt.get();

                                            usedBalance = Functions.roundToTwoDecimalPlaces(
                                                    card.usedBalancePEN()
                                                            + card.usedBalanceUSD() * exchangeRate.sellRate()
                                            );
                                        } else {
                                            // Sin tipo de cambio, no se convierte el saldo en USD.
                                            usedBalance = Functions.roundToTwoDecimalPlaces(
                                                    card.usedBalancePEN() + card.usedBalanceUSD()
                                            );
                                        }

                                        Double remainingBalance =
                                                Functions.roundToTwoDecimalPlaces(
                                                        card.totalBalance() - usedBalance
                                                );

                                        CardResponseDto cardResponse = new CardResponseDto(
                                                card.id(),
                                                card.cardNumber(),
                                                card.totalBalance(),
                                                usedBalance,
                                                remainingBalance,
                                                card.usedBalancePEN(),
                                                card.usedBalanceUSD()
                                        );

                                        cardResponseList.add(cardResponse);
                                    }

                                    return cardResponseList;
                                })
                );
    }

}
