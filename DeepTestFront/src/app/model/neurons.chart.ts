import { Neuron } from "./neuron";

declare var d3;

export class NeuronsChart {

  isUploaded: boolean;

  margin = { top: 55, right: 5, bottom: 5, left: 55 };
  r = 5;
  neuronMaxNum = 16;
  neuronGap = 15;
  layerGap = 20;
  modelGap = 20;

  static delta = 0.000001;

  types = ['未激活', '全激活', '仅变异模型激活', '仅标准模型激活'];
  colors = ['#777', '#5fc', '#c63', '#c36'];
  strokeColor = '#333';

  target: HTMLElement;

  constructor(target: HTMLElement, isUploaded: boolean) {
    this.target = target;
    this.isUploaded = isUploaded;
  }

  render(standardData: number[][], mutationData: number[][], threshold: number, allActive: number[][]) {
    console.log('start rendering');

    let width = this.target.getBoundingClientRect().width;
    let height = 500;

    let space = width - this.neuronMaxNum * this.neuronGap * 2 - this.margin.left - this.margin.right;
    this.modelGap = space > 0 ? space / 2 : this.modelGap;

    d3.select(this.target)
      .append('text')
      .classed('title', true)
      .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap / 2 - 24)
      .attr('y', 25)
      .attr('fill', 'black')
      .attr('style', 'font-size: 12px')
      .text('标准模型');

    d3.select(this.target)
      .append('text')
      .classed('title', true)
      .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap * 3 / 2 + this.modelGap - 24)
      .attr('y', 25)
      .attr('fill', 'black')
      .attr('style', 'font-size: 12px')
      .text('变异模型');

    let formattedStandardData = NeuronsChart.dataFormat(standardData, this.isUploaded);
    let formattedMutationData = NeuronsChart.dataFormat(mutationData, this.isUploaded);

    let top:number = this.margin.top;
    // console.log(allActive);
    // console.log(formattedMutationData);
    for (let index = 0; index < formattedStandardData.length; index++) {
      d3.select(this.target)
        .selectAll('circle .standard-layer' + index)
        .data(formattedStandardData[index])
        .enter()
        .append('circle')
        .classed('standard-layer' + index, true)
        .classed('important', (d, i) => {
          // console.log(i);
          let t = allActive[index];
          return t.indexOf(i) >= 0;
        })
        .attr('r', this.r)
        .attr('style', 'cursor: pointer')
        .attr('fill', d => {
          if (d['value'] - threshold < NeuronsChart.delta) {
            return this.colors[0];
          } else {
            return this.colors[1];
          }
        })
        .attr('stroke', this.strokeColor)
        .attr('stroke-width', (d, i) => {
          let t = allActive[index];
          return t.indexOf(i) >= 0 ? 4 : 1;
        })
        .attr('transform', (d, i) => {
          let offsetX = this.margin.left + (i % this.neuronMaxNum) * this.neuronGap;
          let offsetY = top + Math.trunc(i / this.neuronMaxNum) * this.neuronGap;
          return `translate(${offsetX}, ${offsetY})`;
        })
        .on('click', function(d: Neuron) {
          let t: HTMLElement = document.getElementById('neuron-selected');
          let tiu: HTMLElement = document.getElementById('neuron-isUploaded');
          t['value'] = d.position;
          tiu['value'] = d.isUploaded;
          let ev = document.createEvent('HTMLEvents');
          ev.initEvent('change', false, true);
          t.dispatchEvent(ev);
        })
        .on('mouseover', function(d: Neuron) {
          let pos = d3.mouse(this);
          let tip = document.getElementById('neuron-details');
          let left = this.getBoundingClientRect().left + pos[0];
          let top = this.getBoundingClientRect().top + pos[1];

          tip.style.left = (left + 20) + 'px';
          tip.style.top = (top + 20) + 'px';

          tip.innerHTML = NeuronsChart.getDetails(d.value);

          tip.style.display = 'block';
        })
        .on('mouseout', function() {
          let tip = document.getElementById('neuron-details');
          tip.style.display = 'none';
        })
        .attr('opacity', 0)
        .transition()
        .duration(800)
        .attr('opacity', 1);
      if (index >= formattedMutationData.length) {
        d3.select(this.target)
          .append('text')
          .classed('layer-num', true)
          .attr('x', 0)
          .attr('y', top + Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap / 2)
          .attr('fill', 'black')
          .attr('style', 'font-size: 12px')
          .text('第' + (index + 1) +"层");
        top += (Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap) + this.layerGap;
        continue;
      }
      d3.select(this.target)
        .selectAll('circle .mutation-layer' + index)
        .data(formattedMutationData[index])
        .enter()
        .append('circle')
        .classed('mutation-layer' + index, true)
        .classed('important', (d, i) => {
          let t = allActive[index];
          return t.indexOf(i) >= 0;
        })
        .attr('r', this.r)
        .attr('style', 'cursor: pointer')
        .attr('fill', d => {
          if (d['value'] - threshold < NeuronsChart.delta) {
            return this.colors[0];
          } else {
            return this.colors[1];
          }
        })
        .attr('stroke', this.strokeColor)
        .attr('stroke-width', (d, i) => {
          let t = allActive[index];
          return t.indexOf(i) >= 0 ? 4 : 1;
        })
        .attr('transform', (d, i) => {
          let offsetX:number = this.margin.left + this.neuronMaxNum * this.neuronGap + this.modelGap + (i % this.neuronMaxNum) * this.neuronGap;
          let offsetY:number = top + Math.trunc(i / this.neuronMaxNum) * this.neuronGap;
          return `translate(${offsetX}, ${offsetY})`;
        })
        .on('click', function(d: Neuron) {
          let t:HTMLElement = document.getElementById('neuron-selected');
          let tiu: HTMLElement = document.getElementById('neuron-isUploaded');
          t['value'] = d.position;
          tiu['value'] = d.isUploaded;
          let ev = document.createEvent('HTMLEvents');
          ev.initEvent('change', false, true);
          t.dispatchEvent(ev);
        })
        .on('mouseover', function(d: Neuron) {
          let pos = d3.mouse(this);
          let tip = document.getElementById('neuron-details');
          let left = this.getBoundingClientRect().left + pos[0];
          let top = this.getBoundingClientRect().top + pos[1];

          tip.style.left = (left + 20) + 'px';
          tip.style.top = (top + 20) + 'px';

          tip.innerHTML = NeuronsChart.getDetails(d.value);

          tip.style.display = 'block';
        })
        .on('mouseout', function() {
          let tip = document.getElementById('neuron-details');
          tip.style.display = 'none';
        })
        .attr('opacity', 0)
        .transition()
        .duration(800)
        .attr('opacity', 1);
      d3.select(this.target)
        .append('text')
        .classed('layer-num', true)
        .attr('x', 0)
        .attr('y', top + Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap / 2)
        .attr('fill', 'black')
        .attr('style', 'font-size: 12px')
        .text('第' + (index + 1) +"层");

      top += (Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap) + this.layerGap;
    }

    this.target.style.height = top + 'px';
  }

  renderMix(standardData: number[][], mutationData: number[][], threshold: number, allActive: number[][]): void {
    console.log('start rendering');

    let width = this.target.getBoundingClientRect().width;
    let height = 500;

    let space = width - this.neuronMaxNum * this.neuronGap * 2 - this.margin.left - this.margin.right;
    this.modelGap = space > 0 ? space / 2 : this.modelGap;

    let mixData: number[][] = [];
    let index = 0;

    for (index = 0; index < mutationData.length; index++) {
      let layer = standardData[index];
      let layerMix = [];
      for (let index2 = 0; index2 < layer.length; index2++) {
        let item = {
          standardVal: standardData[index][index2],
          mutationVal: mutationData[index][index2],
          position: [index, index2]
        };
        layerMix.push(item);
      }
      mixData.push(layerMix);
    }

    d3.select(this.target)
      .append('text')
      .classed('title', true)
      .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap / 2 - 24)
      .attr('y', 25)
      .attr('fill', 'black')
      .attr('style', 'font-size: 12px')
      .text('模型对比');

    d3.select(this.target)
      .selectAll('rect .legend')
      .data(this.types)
      .enter()
      .append('rect')
      .classed('legend', true)
      .attr('fill', (d, i) => {
        return this.colors[i];
      })
      .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap + 15)
      .attr('y', (d, i) => {
        return 16 + i * this.neuronGap;
      })
      .attr('width', this.r * 2)
      .attr('height', this.r * 2)
      .attr('stroke', this.strokeColor);

    d3.select(this.target)
      .selectAll('text .legend')
      .data(this.types)
      .enter()
      .append('text')
      .attr('x', this.margin.left + this.neuronMaxNum * this.neuronGap + 25 + this.r * 2 + 5)
      .attr('y', (d, i) => {
        return 25 + i * this.neuronGap;
      })
      .attr('fill', 'black')
      .attr('style', 'font-size: 12px')
      .text(d => {
        return d;
      });

    let top:number = this.margin.top;
    for (index = 0; index < mixData.length; index++) {
      d3.select(this.target)
        .selectAll('circle .standard-layer' + index)
        .data(mixData[index])
        .enter()
        .append('circle')
        .classed('standard-layer' + index, true)
        .classed('important', (d, i) => {
          let t = allActive[index];
          return t.indexOf(i) >= 0;
        })
        .attr('r', this.r)
        .attr('style', 'cursor: pointer')
        .attr('fill', d => {
          if (d['standardVal'] - threshold < NeuronsChart.delta && d['mutationVal'] - threshold < NeuronsChart.delta) {
            return this.colors[0];
          } else if (d['standardVal'] - threshold < NeuronsChart.delta && d['mutationVal'] - threshold >= NeuronsChart.delta) {
            return this.colors[2];
          } else if (d['standardVal'] - threshold >= NeuronsChart.delta && d['mutationVal'] - threshold < NeuronsChart.delta) {
            return this.colors[3];
          } else {
            return this.colors[1];
          }
        })
        .attr('stroke', '#333')
        .attr('stroke-width', (d, i) => {
          let t = allActive[index];
          return t.indexOf(i) >= 0 ? 4 : 1;
        })
        .attr('transform', (d, i) => {
          let offsetX = this.margin.left + (i % this.neuronMaxNum) * this.neuronGap;
          let offsetY = top + Math.trunc(i / this.neuronMaxNum) * this.neuronGap;
          return `translate(${offsetX}, ${offsetY})`;
        })
        .on('click', function(d, i) {
          let t: HTMLElement = document.getElementById('neuron-selected');
          let tiu: HTMLElement = document.getElementById('neuron-isUploaded');
          t['value'] = d.position;
          tiu['value'] = d.isUploaded;
          let ev = document.createEvent('HTMLEvents');
          ev.initEvent('change', false, true);
          t.dispatchEvent(ev);
        })
        .on('mouseover', function(d) {
          let pos = d3.mouse(this);
          let tip = document.getElementById('neuron-details');
          let left = this.getBoundingClientRect().left + pos[0];
          let top = this.getBoundingClientRect().top + pos[1];

          tip.style.left = (left + 20) + 'px';
          tip.style.top = (top + 20) + 'px';

          tip.innerHTML = NeuronsChart.getDetailsMix(d['standardVal'], d['mutationVal']);

          tip.style.display = 'block';
        })
        .on('mouseout', function() {
          let tip = document.getElementById('neuron-details');
          tip.style.display = 'none';
        })
        .attr('opacity', 0)
        .transition()
        .duration(800)
        .attr('opacity', 1);
      d3.select(this.target)
        .append('text')
        .classed('layer-num', true)
        .attr('x', 0)
        .attr('y', top + Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap / 2)
        .attr('fill', 'black')
        .attr('style', 'font-size: 12px')
        .text('第' + (index + 1) +"层");

      top += (Math.trunc(standardData[index].length / this.neuronMaxNum) * this.neuronGap) + this.layerGap;
    }

    this.target.style.height = top + 'px';
  }

  destroy(): void {
    d3.select(this.target)
      .selectAll('circle')
      .remove();
    d3.select(this.target)
      .selectAll('text')
      .remove();
    d3.select(this.target)
      .selectAll('rect')
      .remove();
  }

  private static getDetails(value): string {
    let state;
    if (Math.abs(value) < NeuronsChart.delta) {
      state = '未激活';
    } else {
      state = '激活';
    }
    return `激活状态: ${state}<br/>激活值: ${value == 0 ? value : value.toFixed(6)}`;
  }

  private static getDetailsMix(standardValue, mutationValue): string {
    return `标准模型激活值: ${standardValue == 0 ? standardValue : standardValue.toFixed(6)}
      <br/>变异模型激活值: ${mutationValue == 0 ? mutationValue : mutationValue.toFixed(6)}`;
  }

  private static dataFormat(data: number[][], isUploaded: boolean): Neuron[][] {
    let formattedData = [];
    for (let i = 0; i < data.length; i++) {
      let layer = data[i];
      let layerFormat = [];
      for (let j = 0; j < layer.length; j++) {
        let t = new Neuron();
        t.isUploaded = isUploaded;
        t.position = [i, j];
        t.value = data[i][j];
        layerFormat.push(t);
      }
      formattedData.push(layerFormat);
    }
    return formattedData;
  }

}
