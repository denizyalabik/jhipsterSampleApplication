import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { HistoryComponent } from './history.component';
import { HistoryDetailComponent } from './history-detail.component';
import { HistoryPopupComponent } from './history-dialog.component';
import { HistoryDeletePopupComponent } from './history-delete-dialog.component';

@Injectable()
export class HistoryResolvePagingParams implements Resolve<any> {

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

export const historyRoute: Routes = [
    {
        path: 'history',
        component: HistoryComponent,
        resolve: {
            'pagingParams': HistoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Histories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'history/:id',
        component: HistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Histories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historyPopupRoute: Routes = [
    {
        path: 'history-new',
        component: HistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Histories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'history/:id/edit',
        component: HistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Histories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'history/:id/delete',
        component: HistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Histories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
