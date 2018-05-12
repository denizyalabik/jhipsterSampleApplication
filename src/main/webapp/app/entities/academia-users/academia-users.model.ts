import { BaseEntity } from './../../shared';

export const enum Role {
    'ADMIN',
    'TEACHER',
    'STUDENT'
}

export class AcademiaUsers implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public userName?: string,
        public email?: string,
        public password?: string,
        public uRole?: Role,
        public creates?: BaseEntity[],
        public solves?: BaseEntity[],
        public have?: BaseEntity[],
    ) {
    }
}
