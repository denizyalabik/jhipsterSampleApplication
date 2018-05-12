import { BaseEntity } from './../../shared';

export class History implements BaseEntity {
    constructor(
        public id?: number,
        public solvedDate?: any,
        public givenAnswer?: string,
        public questionAnswer?: BaseEntity,
        public academiaUsers?: BaseEntity,
    ) {
    }
}
