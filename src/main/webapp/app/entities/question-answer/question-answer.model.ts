import { BaseEntity } from './../../shared';

export class QuestionAnswer implements BaseEntity {
    constructor(
        public id?: number,
        public question?: string,
        public correctAnswer?: string,
        public wrongAnswers?: string,
        public user?: BaseEntity,
    ) {
    }
}
