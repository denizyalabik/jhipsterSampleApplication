import { BaseEntity } from './../../shared';

export class TestAc implements BaseEntity {
    constructor(
        public id?: number,
        public createdTime?: any,
        public testName?: string,
        public have?: BaseEntity[],
        public acauser?: BaseEntity,
    ) {
    }
}
