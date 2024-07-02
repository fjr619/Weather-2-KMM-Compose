package data.mapper.to_domain

interface MapperToDomain<Domain, Entity> {
    fun mapToDomain(entity: Entity): Domain
}