package br.com.zup.inventory.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.inventory.entity.TicketInventory;

public interface TicketInventoryRepository extends JpaRepository<TicketInventory, String> {
    List<TicketInventory> findAllByTicketIdIn(Collection<String> ticketIds);
}
