import { Component, OnInit, Input } from '@angular/core';
import { CandidatureAggregate } from './candidature-wz.model';

@Component({
    selector: 'jhi-candidature-aggregate',
    templateUrl: './candidature-aggregate.component.html'
})
export class CandidatureAggregateComponent {

    @Input() aggregate: CandidatureAggregate;
}
