package com.springboot.inventory.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSupply is a Querydsl query type for Supply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSupply extends EntityPathBase<Supply> {

    private static final long serialVersionUID = -805847765L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSupply supply = new QSupply("supply");

    public final QTimestamped _super = new QTimestamped(this);

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath image = createString("image");

    public final StringPath imagePath = createString("imagePath");

    public final StringPath modelContent = createString("modelContent");

    public final StringPath modelName = createString("modelName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath serialNum = createString("serialNum");

    public final EnumPath<com.springboot.inventory.common.enums.SupplyStatusEnum> status = createEnum("status", com.springboot.inventory.common.enums.SupplyStatusEnum.class);

    public final NumberPath<Long> supplyId = createNumber("supplyId", Long.class);

    public final QUser user;

    public QSupply(String variable) {
        this(Supply.class, forVariable(variable), INITS);
    }

    public QSupply(Path<? extends Supply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSupply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSupply(PathMetadata metadata, PathInits inits) {
        this(Supply.class, metadata, inits);
    }

    public QSupply(Class<? extends Supply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

