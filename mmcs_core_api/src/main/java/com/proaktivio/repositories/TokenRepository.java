package com.proaktivio.repositories;

import java.util.Date;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.proaktivio.models.Client;
import com.proaktivio.models.Token;

public interface TokenRepository extends JpaRepository<Token, Long>{

	public Token findByValue(String token);

    public Token findByClient(Client client);

    public Stream<Token> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from Token t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
