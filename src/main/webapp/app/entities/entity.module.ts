import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterSampleApplicationQuestionAnswerModule } from './question-answer/question-answer.module';
import { JhipsterSampleApplicationAcademiaUsersModule } from './academia-users/academia-users.module';
import { JhipsterSampleApplicationHistoryModule } from './history/history.module';
import { JhipsterSampleApplicationTestAcModule } from './test-ac/test-ac.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhipsterSampleApplicationQuestionAnswerModule,
        JhipsterSampleApplicationAcademiaUsersModule,
        JhipsterSampleApplicationHistoryModule,
        JhipsterSampleApplicationTestAcModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterSampleApplicationEntityModule {}
