import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Usersdemo } from './usersdemo.model';
import { UsersdemoService } from './usersdemo.service';

@Component({
    selector: 'jhi-usersdemo-detail',
    templateUrl: './usersdemo-detail.component.html'
})
export class UsersdemoDetailComponent implements OnInit, OnDestroy {

    usersdemo: Usersdemo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private usersdemoService: UsersdemoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUsersdemos();
    }

    load(id) {
        this.usersdemoService.find(id)
            .subscribe((usersdemoResponse: HttpResponse<Usersdemo>) => {
                this.usersdemo = usersdemoResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUsersdemos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'usersdemoListModification',
            (response) => this.load(this.usersdemo.id)
        );
    }
}
