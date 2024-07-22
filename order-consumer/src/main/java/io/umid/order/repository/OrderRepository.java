package io.umid.order.repository;

import io.umid.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUsername(String username);

    Optional<Order> findByIdAndUsername(Integer id, String username);

    @Modifying
    @Query("""
            update Order o
            set o.name = :name, o.details = :details
            where o.id = :orderId and o.username = :username
            """)
    int updateOrder(@Param("orderId") Integer orderId,
                    @Param("username") String username,
                    @Param("name") String name,
                    @Param("details") String details);

    @Modifying
    @Query("""
            delete Order o
            where o.id = :orderId and o.username = :username
            """)
    int deleteOrder(@Param("orderId") Integer orderId,
                    @Param("username") String username);

}
