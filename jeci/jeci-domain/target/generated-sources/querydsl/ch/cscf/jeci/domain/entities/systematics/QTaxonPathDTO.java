package ch.cscf.jeci.domain.entities.systematics;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTaxonPathDTO is a Querydsl query type for TaxonPathDTO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxonPathDTO extends EntityPathBase<TaxonPathDTO> {

    private static final long serialVersionUID = 401725157L;

    public static final QTaxonPathDTO taxonPathDTO = new QTaxonPathDTO("taxonPathDTO");

    public final StringPath authorYear = createString("authorYear");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath path = createString("path");

    public QTaxonPathDTO(String variable) {
        super(TaxonPathDTO.class, forVariable(variable));
    }

    public QTaxonPathDTO(Path<? extends TaxonPathDTO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTaxonPathDTO(PathMetadata metadata) {
        super(TaxonPathDTO.class, metadata);
    }

}

