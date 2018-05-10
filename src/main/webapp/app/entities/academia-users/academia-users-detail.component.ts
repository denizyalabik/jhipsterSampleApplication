import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AcademiaUsers } from './academia-users.model';
import { AcademiaUsersService } from './academia-users.service';

@Component({
    selector: 'jhi-academia-users-detail',
    templateUrl: './academia-users-detail.component.html'
})
export class AcademiaUsersDetailComponent implements OnInit, OnDestroy {

    academiaUsers: AcademiaUsers;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private academiaUsersService: AcademiaUsersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAcademiaUsers();
    }

    load(id) {
        this.academiaUsersService.find(id)
            .subscribe((academiaUsersResponse: HttpResponse<AcademiaUsers>) => {
                this.academiaUsers = academiaUsersResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAcademiaUsers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'academiaUsersListModification',
            (response) => this.load(this.academiaUsers.id)
        );
    }
}
