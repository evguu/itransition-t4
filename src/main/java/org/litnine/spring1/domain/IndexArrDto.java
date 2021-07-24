package org.litnine.spring1.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class IndexArrDto {
    Collection<Long> indexes;
    String action;
}
