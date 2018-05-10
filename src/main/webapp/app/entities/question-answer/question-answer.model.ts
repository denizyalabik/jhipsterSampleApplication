import { BaseEntity } from './../../shared';

export const enum Category {
    'NONE',
    'MATH',
    'PHYSICS',
    'CHEMISTRY'
}

export const enum Subject {
    'ASD',
    'ERED'
}

export const enum Level {
    'EASY',
    'NORMAL',
    'HARD'
}

export class QuestionAnswer implements BaseEntity {
    constructor(
        public id?: number,
        public questionTitle?: string,
        public question?: string,
        public correctAnswer?: string,
        public wrongAnswers?: string,
        public catagory?: Category,
        public subject?: Subject,
        public level?: Level,
        public solveds?: BaseEntity[],
        public acauser?: BaseEntity,
        public ques?: BaseEntity[],
    ) {
    }
}
