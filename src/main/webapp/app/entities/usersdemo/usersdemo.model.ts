import { BaseEntity } from './../../shared';

export const enum Role {
    'ADMIN',
    'TEACHER',
    'STUDENT'
}

export class Usersdemo implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public userName?: string,
        public email?: string,
        public password?: string,
        public uRole?: Role,
        public qanswers?: BaseEntity[],
    ) {
    }
}
