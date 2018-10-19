import { Component, OnInit, Input } from '@angular/core';

import { CandidatAggregate } from './candidat-wz.model';

@Component({
    selector: 'jhi-candidat-aggregate',
    templateUrl: './candidat-aggregate.component.html'
})
export class CandidatAggregateComponent {

    @Input() aggregate: CandidatAggregate;
}
