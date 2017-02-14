package rt.task2.data.domain;

import java.io.Serializable;

public interface AbstractEntity<PK extends Serializable> {

    PK getId();
}
