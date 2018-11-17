import { Component, OnInit, Input, OnChanges, SimpleChanges, SimpleChange } from '@angular/core';

import { CandidatAggregate } from './candidat-wz.model';
import { ngbToDate } from '../../shared/model/format-utils';

@Component({
    selector: 'jhi-candidat-aggregate',
    templateUrl: './candidat-aggregate.component.html'
})
export class CandidatAggregateComponent implements OnChanges {
    @Input() aggregate: CandidatAggregate;
    formatedCandidat: any = {};

    ngOnChanges(changes: SimpleChanges) {
        const agg: SimpleChange = changes.aggregate;
        this.formatedCandidat = agg.currentValue.candidat;
        this.formatedCandidat.dateNaissance = ngbToDate(this.formatedCandidat.dateNaissance);
        this.formatedCandidat.situation = 'Actif';
    }

}
