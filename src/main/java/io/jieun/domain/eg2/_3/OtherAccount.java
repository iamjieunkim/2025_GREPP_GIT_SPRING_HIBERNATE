package io.jieun.domain.eg2._3;

import jakarta.persistence.*;

@Entity
@TableGenerator(
        name = "ACCOUNT_SEQ_TABLE",
        table = "CST_SEQUENCE_CHECK",
        pkColumnName = "OTHER_ACCOUNT_SEQ",
        allocationSize = 1
)
public class OtherAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "ACCOUNT_SEQ_TABLE")
    private Long id;
    private String name;

}
