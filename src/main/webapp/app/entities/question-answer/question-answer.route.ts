import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { QuestionAnswerComponent } from './question-answer.component';
import { QuestionAnswerDetailComponent } from './question-answer-detail.component';
import { QuestionAnswerPopupComponent } from './question-answer-dialog.component';
import { QuestionAnswerDeletePopupComponent } from './question-answer-delete-dialog.component';

@Injectable()
export class QuestionAnswerResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const questionAnswerRoute: Routes = [
    {
        path: 'question-answer',
        component: QuestionAnswerComponent,
        resolve: {
            'pagingParams': QuestionAnswerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionAnswers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'question-answer/:id',
        component: QuestionAnswerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionAnswers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const questionAnswerPopupRoute: Routes = [
    {
        path: 'question-answer-new',
        component: QuestionAnswerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionAnswers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'question-answer/:id/edit',
        component: QuestionAnswerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionAnswers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'question-answer/:id/delete',
        component: QuestionAnswerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'QuestionAnswers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
