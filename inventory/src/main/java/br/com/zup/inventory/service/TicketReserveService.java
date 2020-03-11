package br.com.zup.inventory.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.zup.inventory.entity.TicketInventory;
import br.com.zup.inventory.exception.TicketNotAvailableException;
import br.com.zup.inventory.exception.TicketNotFoundException;
import br.com.zup.inventory.repository.TicketInventoryRepository;

@Service
public class TicketReserveService {

    private TicketInventoryRepository ticketInventoryRepository;

    public TicketReserveService(TicketInventoryRepository ticketInventoryRepository) {
        this.ticketInventoryRepository = ticketInventoryRepository;
    }

    @Transactional
    public synchronized void reserveTickets(Map<String, Integer> orderEntry) {
        final List<TicketInventory> tickets = ticketInventoryRepository.findAllByTicketIdIn(orderEntry.keySet());

        for (String ticketId: orderEntry.keySet()) {
            if (tickets.stream().noneMatch(x -> x.getTicketId().equalsIgnoreCase(ticketId)))
                throw new TicketNotFoundException(ticketId);
        }

        for (TicketInventory ticketInventory: tickets) {
            Integer required = orderEntry.get(ticketInventory.getTicketId());

            Integer available = ticketInventory.getAvailable();

            if (available < required)
                throw new TicketNotAvailableException(ticketInventory.getTicketId(), available, required);

            ticketInventory.setAvailable(available - required);
        }

        ticketInventoryRepository.saveAll(tickets);
    }
}
