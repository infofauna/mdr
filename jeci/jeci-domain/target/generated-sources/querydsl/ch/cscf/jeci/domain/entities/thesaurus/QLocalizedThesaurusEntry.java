package ch.cscf.jeci.domain.entities.thesaurus;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocalizedThesaurusEntry is a Querydsl query type for LocalizedThesaurusEntry
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLocalizedThesaurusEntry extends EntityPathBase<LocalizedThesaurusEntry> {

    private static final long serialVersionUID = -834547967L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocalizedThesaurusEntry localizedThesaurusEntry = new QLocalizedThesaurusEntry("localizedThesaurusEntry");

    public final StringPath code = createString("code");

    public final StringPath designation = createString("designation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath language = createString("language");

    public final StringPath parentValueCode = createString("parentValueCode");

    public final NumberPath<Long> parentValueId = createNumber("parentValueId", Long.class);

    public final StringPath realmCode = createString("realmCode");

    public final StringPath realmDefaultLanguage = createString("realmDefaultLanguage");

    public final StringPath realmDesignation = createString("realmDesignation");

    public final StringPath type = createString("type");

    public final QThesaurusValue value;

    public final NumberPath<Long> valueId = createNumber("valueId", Long.class);

    public final NumberPath<Long> valueSortOrder = createNumber("valueSortOrder", Long.class);

    public QLocalizedThesaurusEntry(String variable) {
        this(LocalizedThesaurusEntry.class, forVariable(variable), INITS);
    }

    public QLocalizedThesaurusEntry(Path<? extends LocalizedThesaurusEntry> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocalizedThesaurusEntry(PathMetadata metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QLocalizedThesaurusEntry(PathMetadata metadata, PathInits inits) {
        this(LocalizedThesaurusEntry.class, metadata, inits);
    }

    public QLocalizedThesaurusEntry(Class<? extends LocalizedThesaurusEntry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.value = inits.isInitialized("value") ? new QThesaurusValue(forProperty("value"), inits.get("value")) : null;
    }

}

