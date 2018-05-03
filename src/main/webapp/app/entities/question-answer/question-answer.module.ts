import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from '../../shared';
import {
    QuestionAnswerService,
    QuestionAnswerPopupService,
    QuestionAnswerComponent,
    QuestionAnswerDetailComponent,
    QuestionAnswerDialogComponent,
    QuestionAnswerPopupComponent,
    QuestionAnswerDeletePopupComponent,
    QuestionAnswerDeleteDialogComponent,
    questionAnswerRoute,
    questionAnswerPopupRoute,
    QuestionAnswerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...questionAnswerRoute,
    ...questionAnswerPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSampleApplicationSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        QuestionAnswerComponent,
        QuestionAnswerDetailComponent,
        QuestionAnswerDialogComponent,
        QuestionAnswerDeleteDialogComponent,
        QuestionAnswerPopupComponent,
        QuestionAnswerDeletePopupComponent,
    ],
    entryComponents: [
        QuestionAnswerComponent,
        QuestionAnswerDialogComponent,
        QuestionAnswerPopupComponent,
        QuestionAnswerDeleteDialogComponent,
        QuestionAnswerDeletePopupComponent,
    ],
    providers: [
        QuestionAnswerService,
        QuestionAnswerPopupService,
        QuestionAnswerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationQuestionAnswerModule {}
