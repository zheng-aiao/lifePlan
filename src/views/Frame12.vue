<template>
  <div class="scroll-container">
    <div id="1_2" class="Pixso-frame-1_2">
      <Header />
      <div id="4_2" class="Pixso-frame-4_2">
        <AsideLeft 
          @viewChange="handleViewChange" 
          @categoryClick="handleCategoryClick" 
          @newTask="handleNewTask" 
        />
        <div id="1_62" class="Pixso-frame-1_62">
          <div id="1_124" class="Pixso-frame-1_124">
            <div id="1_125" class="Pixso-frame-1_125">
              <div id="1_126" class="Pixso-frame-1_126">
                <div id="1_130" class="Pixso-frame-1_130">
                  <p id="1_131" class="Pixso-paragraph-1_131">
                    {{ "2025年3月25日 · 星期日 · 今日重点" }}
                  </p>
                </div>
                <div id="1_132" class="Pixso-frame-1_132">
                  <div id="1_133" class="Pixso-frame-1_133">
                    <div id="1_134" class="Pixso-vector-1_134"></div>
                  </div>
                  <div id="1_136" class="Pixso-frame-1_136">
                    <div id="1_137" class="Pixso-vector-1_137"></div>
                  </div>
                </div>
              </div>
            </div>
            <div id="1_139" class="Pixso-frame-1_139">
              <div id="1_140" class="Pixso-rectangle-1_140"></div>
              <div id="1_141" class="Pixso-frame-1_141">
                <div id="1_142" class="Pixso-frame-1_142">
                  <div id="1_143" class="stroke-wrapper-1_143">
                    <div class="Pixso-frame-1_143">
                      <div id="1_144" class="Pixso-frame-1_144">
                        <div id="1_145" class="Pixso-frame-1_145">
                          <p id="1_146" class="Pixso-paragraph-1_146">
                            {{ "深层专注" }}
                          </p>
                        </div>
                        <div id="1_147" class="Pixso-frame-1_147">
                          <p id="1_148" class="Pixso-paragraph-1_148">
                            {{ "60 分钟" }}
                          </p>
                        </div>
                      </div>
                      <div id="1_149" class="Pixso-frame-1_149">
                        <p id="1_150" class="Pixso-paragraph-1_150">
                          {{ "撰写 Chronicle 专题文章" }}
                        </p>
                      </div>
                      <div id="1_151" class="Pixso-frame-1_151">
                        <p id="1_152" class="Pixso-paragraph-1_152">
                          {{ "整理过去三周的编辑逻辑与视觉资产..." }}
                        </p>
                      </div>
                    </div>
                    <div class="stroke-1_143"></div>
                  </div>
                  <p id="1_153" class="Pixso-paragraph-1_153">
                    {{ "08:00" }}
                  </p>
                </div>
                <div id="1_154" class="Pixso-frame-1_154">
                  <div id="1_155" class="stroke-wrapper-1_155">
                    <div class="Pixso-frame-1_155">
                      <div id="1_156" class="Pixso-frame-1_156">
                        <div id="1_157" class="Pixso-frame-1_157">
                          <div id="1_158" class="Pixso-frame-1_158">
                            <p id="1_159" class="Pixso-paragraph-1_159">
                              {{ "正在进行" }}
                            </p>
                          </div>
                          <div id="1_160" class="Pixso-rectangle-1_160"></div>
                        </div>
                        <div id="1_161" class="Pixso-frame-1_161">
                          <p id="1_162" class="Pixso-paragraph-1_162">
                            {{ "120 分钟" }}
                          </p>
                        </div>
                      </div>
                      <div id="1_163" class="Pixso-frame-1_163">
                        <p id="1_164" class="Pixso-paragraph-1_164">
                          {{ "产品策略同步会议" }}
                        </p>
                      </div>
                      <div id="1_165" class="Pixso-frame-1_165">
                        <div id="1_166" class="Pixso-frame-1_166"></div>
                        <div id="1_167" class="Pixso-frame-1_167">
                          <div id="1_168" class="Pixso-frame-1_168"></div>
                        </div>
                        <div id="1_169" class="Pixso-frame-1_169">
                          <div id="1_170" class="stroke-wrapper-1_170">
                            <div class="Pixso-frame-1_170">
                              <p id="1_171" class="Pixso-paragraph-1_171">
                                {{ "+3" }}
                              </p>
                            </div>
                            <div class="stroke-1_170"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="stroke-1_155"></div>
                  </div>
                  <p id="1_172" class="Pixso-paragraph-1_172">
                    {{ "10:30" }}
                  </p>
                </div>
                <div id="1_173" class="Pixso-frame-1_173">
                  <div id="1_174" class="stroke-wrapper-1_174">
                    <div class="Pixso-frame-1_174">
                      <div id="1_175" class="Pixso-frame-1_175">
                        <div id="1_176" class="Pixso-frame-1_176">
                          <p id="1_177" class="Pixso-paragraph-1_177">
                            {{ "体能恢复" }}
                          </p>
                        </div>
                        <div id="1_178" class="Pixso-frame-1_178">
                          <p id="1_179" class="Pixso-paragraph-1_179">
                            {{ "45 分钟" }}
                          </p>
                        </div>
                      </div>
                      <div id="1_180" class="Pixso-frame-1_180">
                        <p id="1_181" class="Pixso-paragraph-1_181">
                          {{ "核心力量与拉伸" }}
                        </p>
                      </div>
                      <div id="1_182" class="Pixso-frame-1_182">
                        <p id="1_183" class="Pixso-paragraph-1_183">
                          {{ "针对背部和颈部肌肉的针对性训练" }}
                        </p>
                      </div>
                    </div>
                    <div class="stroke-1_174"></div>
                  </div>
                  <p id="1_184" class="Pixso-paragraph-1_184">
                    {{ "14:00" }}
                  </p>
                </div>
                <div id="1_185" class="Pixso-frame-1_185">
                  <p id="1_186" class="Pixso-paragraph-1_186">
                    {{ "16:30" }}
                  </p>
                  <div id="1_187" class="stroke-wrapper-1_187">
                    <div class="Pixso-frame-1_187">
                      <div id="1_188" class="Pixso-frame-1_188">
                        <div id="1_189" class="Pixso-frame-1_189">
                          <p id="1_190" class="Pixso-paragraph-1_190">
                            {{ "待定" }}
                          </p>
                        </div>
                        <div id="1_191" class="Pixso-frame-1_191">
                          <p id="1_192" class="Pixso-paragraph-1_192">
                            {{ "30 分钟" }}
                          </p>
                        </div>
                      </div>
                      <div id="1_193" class="Pixso-frame-1_193">
                        <p id="1_194" class="Pixso-paragraph-1_194">
                          {{ "回复待办邮件" }}
                        </p>
                      </div>
                    </div>
                    <div class="stroke-1_187"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div id="1_63" class="Pixso-frame-1_63">
            <div id="1_64" class="Pixso-frame-1_64">
              <div id="1_65" class="Pixso-frame-1_65">
                <div id="1_66" class="Pixso-frame-1_66">
                  <p id="1_67" class="Pixso-paragraph-1_67">
                    {{ "年度任务" }}
                  </p>
                </div>
                <div id="1_68" class="Pixso-frame-1_68">
                  <p id="1_69" class="Pixso-paragraph-1_69">
                    {{ "2024" }}
                  </p>
                </div>
              </div>
              <div id="5_18" class="Pixso-frame-5_18">
                <div id="1_70" class="Pixso-frame-1_70">
                  <div id="1_71" class="Pixso-frame-1_71">
                    <div id="1_72" class="Pixso-frame-1_72">
                      <p id="1_73" class="Pixso-paragraph-1_73">
                        {{ "完成品牌设计规范指南" }}
                      </p>
                      <div id="1_74" class="Pixso-frame-1_74">
                        <p id="1_75" class="Pixso-paragraph-1_75">
                          {{ "工作" }}
                        </p>
                      </div>
                    </div>
                    <div id="1_76" class="Pixso-frame-1_76">
                      <div id="1_77" class="Pixso-frame-1_77">
                        <div id="1_78" class="Pixso-rectangle-1_78"></div>
                      </div>
                      <div id="1_79" class="Pixso-frame-1_79">
                        <p id="1_80" class="Pixso-paragraph-1_80">
                          {{ "75%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="1_81" class="Pixso-rectangle-1_81"></div>
                </div>
                <div id="5_19" class="Pixso-frame-5_19">
                  <div id="5_20" class="Pixso-frame-5_20">
                    <div id="5_21" class="Pixso-frame-5_21">
                      <p id="5_22" class="Pixso-paragraph-5_22">
                        {{ "阅读 4 本深度学习书籍" }}
                      </p>
                      <div id="5_23" class="Pixso-frame-5_23">
                        <p id="5_24" class="Pixso-paragraph-5_24">
                          {{ "学习" }}
                        </p>
                      </div>
                    </div>
                    <div id="5_25" class="Pixso-frame-5_25">
                      <div id="5_26" class="Pixso-frame-5_26">
                        <div id="5_27" class="Pixso-rectangle-5_27"></div>
                      </div>
                      <div id="5_28" class="Pixso-frame-5_28">
                        <p id="5_29" class="Pixso-paragraph-5_29">
                          {{ "25%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="5_30" class="Pixso-rectangle-5_30"></div>
                </div>
                <div id="5_31" class="Pixso-frame-5_31">
                  <div id="5_32" class="Pixso-frame-5_32">
                    <div id="5_33" class="Pixso-frame-5_33">
                      <p id="5_34" class="Pixso-paragraph-5_34">
                        {{ "半程马拉松训练营" }}
                      </p>
                      <div id="5_35" class="Pixso-frame-5_35">
                        <p id="5_36" class="Pixso-paragraph-5_36">
                          {{ "健身" }}
                        </p>
                      </div>
                    </div>
                    <div id="5_37" class="Pixso-frame-5_37">
                      <div id="5_38" class="Pixso-frame-5_38">
                        <div id="5_39" class="Pixso-rectangle-5_39"></div>
                      </div>
                      <div id="5_40" class="Pixso-frame-5_40">
                        <p id="5_41" class="Pixso-paragraph-5_41">
                          {{ "60%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="5_42" class="Pixso-rectangle-5_42"></div>
                </div>
              </div>
            </div>
            <div id="1_82" class="Pixso-frame-1_82">
              <div id="7_76" class="Pixso-frame-7_76">
                <div id="7_77" class="Pixso-frame-7_77">
                  <p id="7_85" class="Pixso-paragraph-7_85">
                    {{ "月度任务" }}
                  </p>
                </div>
                <div id="7_79" class="Pixso-frame-7_79">
                  <p id="7_80" class="Pixso-paragraph-7_80">
                    {{ "2024" }}
                  </p>
                </div>
              </div>
              <div id="1_85" class="Pixso-frame-1_85">
                <div id="1_86" class="Pixso-frame-1_86">
                  <div id="1_87" class="Pixso-frame-1_87">
                    <div id="1_88" class="Pixso-frame-1_88">
                      <p id="1_89" class="Pixso-paragraph-1_89">
                        {{ "阅读 4 本深度学习书籍" }}
                      </p>
                      <div id="1_90" class="Pixso-frame-1_90">
                        <p id="1_91" class="Pixso-paragraph-1_91">
                          {{ "学习" }}
                        </p>
                      </div>
                    </div>
                    <div id="1_92" class="Pixso-frame-1_92">
                      <div id="1_93" class="Pixso-frame-1_93">
                        <div id="1_94" class="Pixso-rectangle-1_94"></div>
                      </div>
                      <div id="1_95" class="Pixso-frame-1_95">
                        <p id="1_96" class="Pixso-paragraph-1_96">
                          {{ "25%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="1_97" class="Pixso-rectangle-1_97"></div>
                </div>
                <div id="1_98" class="Pixso-frame-1_98">
                  <div id="1_99" class="Pixso-frame-1_99">
                    <div id="1_100" class="Pixso-frame-1_100">
                      <p id="1_101" class="Pixso-paragraph-1_101">
                        {{ "半程马拉松训练营" }}
                      </p>
                      <div id="1_102" class="Pixso-frame-1_102">
                        <p id="1_103" class="Pixso-paragraph-1_103">
                          {{ "健身" }}
                        </p>
                      </div>
                    </div>
                    <div id="1_104" class="Pixso-frame-1_104">
                      <div id="1_105" class="Pixso-frame-1_105">
                        <div id="1_106" class="Pixso-rectangle-1_106"></div>
                      </div>
                      <div id="1_107" class="Pixso-frame-1_107">
                        <p id="1_108" class="Pixso-paragraph-1_108">
                          {{ "60%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="1_109" class="Pixso-rectangle-1_109"></div>
                </div>
              </div>
            </div>
            <div id="1_110" class="Pixso-frame-1_110">
              <div id="7_87" class="Pixso-frame-7_87">
                <div id="7_88" class="Pixso-frame-7_88">
                  <p id="1_112" class="Pixso-paragraph-1_112">
                    {{ "临时任务" }}
                  </p>
                </div>
                <div id="7_90" class="Pixso-frame-7_90">
                  <p id="7_91" class="Pixso-paragraph-7_91">
                    {{ "2024" }}
                  </p>
                </div>
              </div>
              <div id="6_44" class="Pixso-frame-6_44">
                <div id="6_45" class="Pixso-frame-6_45">
                  <div id="6_46" class="Pixso-frame-6_46">
                    <div id="6_47" class="Pixso-frame-6_47">
                      <p id="6_48" class="Pixso-paragraph-6_48">
                        {{ "阅读 4 本深度学习书籍" }}
                      </p>
                      <div id="6_49" class="Pixso-frame-6_49">
                        <p id="6_50" class="Pixso-paragraph-6_50">
                          {{ "学习" }}
                        </p>
                      </div>
                    </div>
                    <div id="6_51" class="Pixso-frame-6_51">
                      <div id="6_52" class="Pixso-frame-6_52">
                        <div id="6_53" class="Pixso-rectangle-6_53"></div>
                      </div>
                      <div id="6_54" class="Pixso-frame-6_54">
                        <p id="6_55" class="Pixso-paragraph-6_55">
                          {{ "25%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="6_56" class="Pixso-rectangle-6_56"></div>
                </div>
                <div id="6_57" class="Pixso-frame-6_57">
                  <div id="6_58" class="Pixso-frame-6_58">
                    <div id="6_59" class="Pixso-frame-6_59">
                      <p id="6_60" class="Pixso-paragraph-6_60">
                        {{ "半程马拉松训练营" }}
                      </p>
                      <div id="6_61" class="Pixso-frame-6_61">
                        <p id="6_62" class="Pixso-paragraph-6_62">
                          {{ "健身" }}
                        </p>
                      </div>
                    </div>
                    <div id="6_63" class="Pixso-frame-6_63">
                      <div id="6_64" class="Pixso-frame-6_64">
                        <div id="6_65" class="Pixso-rectangle-6_65"></div>
                      </div>
                      <div id="6_66" class="Pixso-frame-6_66">
                        <p id="6_67" class="Pixso-paragraph-6_67">
                          {{ "60%" }}
                        </p>
                      </div>
                    </div>
                  </div>
                  <div id="6_68" class="Pixso-rectangle-6_68"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <AsideRight />
      </div>
    </div>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue'
import AsideLeft from '@/components/AsideLeft.vue'
import AsideRight from '@/components/AsideRight.vue'

const handleViewChange = (view) => {
  console.log('View changed to:', view)
}

const handleCategoryClick = (category) => {
  console.log('Category clicked:', category)
}

const handleNewTask = () => {
  console.log('New task button clicked')
}
</script>

<style>
.scroll-container {
  height: 100%;
  width: 100%;
  overflow: auto;
}

.Pixso-frame-1_2 {
  width: 120rem;
  height: 67.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 0 20rem 0 16rem;
  background-color: rgba(244, 246, 255, 1);
}

.Pixso-frame-4_2 {
  width: 120rem;
  height: 63.5rem;
  position: absolute;
  left: 0;
  top: 4rem;
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
  align-items: center;
}

.Pixso-frame-1_62 {
  width: 84rem;
  height: 63.5rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_124 {
  width: 51.75rem;
  height: 63.5rem;
  position: absolute;
  left: 30.75rem;
  top: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0 2.5rem 0;
}

.Pixso-frame-1_125 {
  width: 51.75rem;
  height: 3.5rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 1rem 0 0 0;
}

.Pixso-frame-1_126 {
  width: 51.75rem;
  height: 2.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 0 0.6875rem 0 0.6875rem;
}

.Pixso-frame-1_130 {
  width: 44.875rem;
  height: 2.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.Pixso-paragraph-1_131 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.175rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(74, 64, 224, 1);
  width: auto;
  height: auto;
  position: relative;
  flex-shrink: 0;
  white-space: pre;
  flex-grow: 0;
}

.Pixso-frame-1_132 {
  width: 5.5rem;
  height: 2.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
}

.Pixso-frame-1_133 {
  width: 2.5rem;
  height: 2.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-vector-1_134 {
  width: 0.4625rem;
  height: 0.75rem;
  background-image: url(@/assets/images/Frame_1_134.png);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.Pixso-frame-1_136 {
  width: 2.5rem;
  height: 2.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-vector-1_137 {
  width: 0.4625rem;
  height: 0.75rem;
  background-image: url(@/assets/images/Frame_1_137.png);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.Pixso-frame-1_139 {
  width: calc(100% - 0rem);
  height: calc(100% - -44.5rem);
  overflow: hidden;
  position: absolute;
  left: 0;
  right: 0;
  top: 4.25rem;
  bottom: -48.75rem;
  display: flex;
  flex-direction: column;
  padding: 0 1rem 0 0;
}

.Pixso-rectangle-1_140 {
  width: 0.0625rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 5rem;
  top: 0;
  bottom: 0;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-frame-1_141 {
  width: 50.75rem;
  height: 42rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 3rem;
  padding: 1rem 0 1rem 0;
}

.Pixso-frame-1_142 {
  width: 50.75rem;
  height: 8rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0 0 8rem;
}

.Pixso-frame-1_143 {
  width: 100%;
  height: 100%;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1.5rem 1.5rem 1.5rem 1.5rem;
  border-radius: 1rem 1rem 1rem 1rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.stroke-wrapper-1_143 {
  position: relative;
  width: 42.75rem;
  height: 8rem;
  display: flex;
  flex-shrink: 0;
  padding: 0 0 0 0.25rem;
}

.stroke-1_143 {
  position: absolute;
  inset: 0;
  border-radius: 1rem 1rem 1rem 1rem;
  border-width: 0 0 0 0.25rem;
  border-style: solid;
  pointer-events: none;
  box-sizing: border-box;
  border-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-1_144 {
  width: 39.5rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 33.405rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_145 {
  width: 2.75rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_146 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0625rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(129, 81, 0, 1);
  width: 2.75rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_147 {
  width: 2.6575rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_148 {
  font-size: 0.75rem;
  font-family: "Inter-Medium";
  font-weight: 500;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1rem;
  color: rgba(104, 120, 143, 1);
  width: 2.6575rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_149 {
  width: 39.5rem;
  height: 1.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_150 {
  font-size: 1.125rem;
  font-family: "Inter-Semi Bold";
  font-weight: 600;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.75rem;
  color: rgba(32, 48, 68, 1);
  width: 39.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
}

.Pixso-frame-1_151 {
  width: 39.5rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_152 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(77, 93, 115, 1);
  width: 39.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
}

.Pixso-paragraph-1_153 {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  color: rgba(158, 174, 199, 1);
  width: 2.5725rem;
  height: 1rem;
  position: absolute;
  left: 0;
  top: 0;
}

.Pixso-frame-1_154 {
  width: 50.75rem;
  height: 8.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0 0 8rem;
}

.Pixso-frame-1_155 {
  width: 100%;
  height: 100%;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1.5rem 1.5rem 1.5rem 1.5rem;
  border-radius: 1rem 1rem 1rem 1rem;
  background-color: rgba(74, 64, 224, 0.05000000074505806);
}

.stroke-wrapper-1_155 {
  position: relative;
  width: 42.75rem;
  height: 8.25rem;
  display: flex;
  flex-shrink: 0;
  padding: 0 0 0 0.25rem;
}

.stroke-1_155 {
  position: absolute;
  inset: 0;
  border-radius: 1rem 1rem 1rem 1rem;
  border-width: 0 0 0 0.25rem;
  border-style: solid;
  pointer-events: none;
  box-sizing: border-box;
  border-color: rgba(74, 64, 224, 1);
}

.Pixso-frame-1_156 {
  width: 39.5rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 32.2188rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_157 {
  width: 3.625rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  align-items: center;
}

.Pixso-frame-1_158 {
  width: 2.75rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_159 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0625rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 64, 224, 1);
  width: 2.75rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_160 {
  width: 0.375rem;
  height: 0.375rem;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(74, 64, 224, 1);
}

.Pixso-frame-1_161 {
  width: 2.9688rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_162 {
  font-size: 0.75rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1rem;
  color: rgba(74, 64, 224, 1);
  width: 2.9688rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_163 {
  width: 39.5rem;
  height: 1.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_164 {
  font-size: 1.125rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.75rem;
  color: rgba(32, 48, 68, 1);
  width: 39.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
}

.Pixso-frame-1_165 {
  width: 39.5rem;
  height: 1.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
}

.Pixso-frame-1_166 {
  width: 1.5rem;
  height: 1.5rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  margin-right: -0.5rem;
  max-width: 38.8125rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-size: 100% 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-image: url(@/assets/images/Frame_1_166.png);
}

.Pixso-frame-1_167 {
  width: 1.5rem;
  height: 1.5rem;
  position: relative;
  flex-shrink: 0;
  margin-right: -0.5rem;
  display: flex;
  flex-direction: column;
  max-width: 38.3125rem;
}

.Pixso-frame-1_168 {
  width: 1.5rem;
  height: 1.5rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  max-width: 38.8125rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-size: 100% 100%;
  background-position: center;
  background-repeat: no-repeat;
  background-image: url(@/assets/images/Frame_1_168.png);
}

.Pixso-frame-1_169 {
  width: 1.5rem;
  height: 1.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-frame-1_170 {
  width: 100%;
  height: 100%;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(151, 149, 255, 1);
}

.stroke-wrapper-1_170 {
  position: relative;
  width: 1.5rem;
  height: 1.5rem;
  display: flex;
  flex-shrink: 0;
  padding: 0.125rem 0.125rem 0.125rem 0.125rem;
}

.stroke-1_170 {
  position: absolute;
  inset: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  border-width: 0.125rem;
  border-style: solid;
  pointer-events: none;
  box-sizing: border-box;
  border-color: rgba(255, 255, 255, 1);
}

.Pixso-paragraph-1_171 {
  font-size: 0.5rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.75rem;
  color: rgba(20, 0, 126, 1);
  width: 0.6513rem;
  height: 0.75rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-paragraph-1_172 {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  color: rgba(158, 174, 199, 1);
  width: 2.3837rem;
  height: 1rem;
  position: absolute;
  left: 0;
  top: 0;
}

.Pixso-frame-1_173 {
  width: 50.75rem;
  height: 8rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0 0 8rem;
}

.Pixso-frame-1_174 {
  width: 100%;
  height: 100%;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1.5rem 1.5rem 1.5rem 1.5rem;
  border-radius: 1rem 1rem 1rem 1rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.stroke-wrapper-1_174 {
  position: relative;
  width: 42.75rem;
  height: 8rem;
  display: flex;
  flex-shrink: 0;
  padding: 0 0 0 0.25rem;
}

.stroke-1_174 {
  position: absolute;
  inset: 0;
  border-radius: 1rem 1rem 1rem 1rem;
  border-width: 0 0 0 0.25rem;
  border-style: solid;
  pointer-events: none;
  box-sizing: border-box;
  border-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_175 {
  width: 39.5rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 33.4181rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_176 {
  width: 2.75rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_177 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0625rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 105, 71, 1);
  width: 2.75rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_178 {
  width: 2.6444rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_179 {
  font-size: 0.75rem;
  font-family: "Inter-Medium";
  font-weight: 500;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1rem;
  color: rgba(104, 120, 143, 1);
  width: 2.6444rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_180 {
  width: 39.5rem;
  height: 1.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_181 {
  font-size: 1.125rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.75rem;
  color: rgba(32, 48, 68, 1);
  width: 39.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
}

.Pixso-frame-1_182 {
  width: 39.5rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_183 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(77, 93, 115, 1);
  width: 39.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
}

.Pixso-paragraph-1_184 {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  color: rgba(158, 174, 199, 1);
  width: 2.4044rem;
  height: 1rem;
  position: absolute;
  left: 0;
  top: 0;
}

.Pixso-frame-1_185 {
  width: 50.75rem;
  height: 6.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0 0 8rem;
}

.Pixso-paragraph-1_186 {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  color: rgba(158, 174, 199, 1);
  width: 2.3563rem;
  height: 1rem;
  position: absolute;
  left: 0;
  top: 0;
}

.Pixso-frame-1_187 {
  width: 100%;
  height: 100%;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1.5rem 1.5rem 2rem 1.5rem;
  border-radius: 1rem 1rem 1rem 1rem;
  background-color: rgba(234, 241, 255, 1);
  opacity: 0.6000000238418579;
}

.stroke-wrapper-1_187 {
  position: relative;
  width: 42.75rem;
  height: 6.75rem;
  display: flex;
  flex-shrink: 0;
  padding: 0 0 0 0.25rem;
}

.stroke-1_187 {
  position: absolute;
  inset: 0;
  border-radius: 1rem 1rem 1rem 1rem;
  border-width: 0 0 0 0.25rem;
  border-style: solid;
  pointer-events: none;
  box-sizing: border-box;
  border-color: rgba(158, 174, 199, 1);
}

.Pixso-frame-1_188 {
  width: 39.5rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 34.7831rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_189 {
  width: 1.375rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_190 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0625rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(104, 120, 143, 1);
  width: 1.375rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_191 {
  width: 2.6544rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_192 {
  font-size: 0.75rem;
  font-family: "Inter-Medium";
  font-weight: 500;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1rem;
  color: rgba(104, 120, 143, 1);
  width: 2.6544rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_193 {
  width: 39.5rem;
  height: 1.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_194 {
  font-size: 1.125rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.75rem;
  color: rgba(32, 48, 68, 1);
  width: 39.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
}

.Pixso-frame-1_63 {
  width: 26.25rem;
  height: 63.5rem;
  position: absolute;
  left: 2rem;
  top: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1.25rem 0 1rem 0;
}

.Pixso-frame-1_64 {
  width: 26.25rem;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.Pixso-frame-1_65 {
  width: 26.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 19.8019rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_66 {
  width: 3.8506rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_67 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(32, 48, 68, 1);
  width: 3.8506rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_68 {
  width: 2.5975rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(210, 228, 255, 1);
}

.Pixso-paragraph-1_69 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(74, 64, 224, 1);
  width: 1.5975rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_18 {
  width: 26.25rem;
  height: 17.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-1_70 {
  width: 26.25rem;
  height: 3.75rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-1_71 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-1_72 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.25rem;
  justify-content: flex-start;
}

.Pixso-paragraph-1_73 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.75rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_74 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(151, 149, 255, 1);
}

.Pixso-paragraph-1_75 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(20, 0, 126, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_76 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-1_77 {
  width: 22.135rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-1_78 {
  width: 75%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 25%;
  top: 0;
  bottom: 0;
  background-color: rgba(74, 64, 224, 1);
}

.Pixso-frame-1_79 {
  width: 1.365rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_80 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.365rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_81 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(74, 64, 224, 1);
}

.Pixso-frame-5_19 {
  width: 26.25rem;
  height: 3.75rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-5_20 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-5_21 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.1006rem;
  justify-content: flex-start;
}

.Pixso-paragraph-5_22 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.8994rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_23 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-paragraph-5_24 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 44, 0, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_25 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-5_26 {
  width: 22.0994rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-5_27 {
  width: 25%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 75%;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-5_28 {
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-5_29 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-5_30 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-5_31 {
  width: 26.25rem;
  height: 3.75rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-5_32 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-5_33 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 15rem;
  justify-content: flex-start;
}

.Pixso-paragraph-5_34 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 7rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_35 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(105, 246, 184, 1);
}

.Pixso-paragraph-5_36 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 90, 60, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-5_37 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-5_38 {
  width: 22.0594rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-5_39 {
  width: 60%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 40%;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-5_40 {
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-5_41 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-5_42 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_82 {
  width: 26.25rem;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.Pixso-frame-7_76 {
  width: 26.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 19.8019rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-7_77 {
  width: 3.8125rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-7_85 {
  font-size: 0.875rem;
  font-family: "Inter-Semi Bold";
  font-weight: 600;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(0, 0, 0, 1);
  width: auto;
  height: auto;
  position: relative;
  flex-shrink: 0;
  white-space: pre;
  flex-grow: 0;
}

.Pixso-frame-7_79 {
  width: 2.5975rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(210, 228, 255, 1);
}

.Pixso-paragraph-7_80 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(74, 64, 224, 1);
  width: 1.5975rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_85 {
  width: 26.25rem;
  height: 17.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
}

.Pixso-frame-1_86 {
  width: 26.25rem;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-1_87 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-1_88 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.1006rem;
  justify-content: flex-start;
}

.Pixso-paragraph-1_89 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.8994rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_90 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-paragraph-1_91 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 44, 0, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_92 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-1_93 {
  width: 22.0994rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-1_94 {
  width: 25%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 75%;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-1_95 {
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_96 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_97 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-1_98 {
  width: 26.25rem;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-1_99 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-1_100 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 15rem;
  justify-content: flex-start;
}

.Pixso-paragraph-1_101 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 7rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_102 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(105, 246, 184, 1);
}

.Pixso-paragraph-1_103 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 90, 60, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-1_104 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-1_105 {
  width: 22.0594rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-1_106 {
  width: 60%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 40%;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_107 {
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_108 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-1_109 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-1_110 {
  width: 26.25rem;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.Pixso-frame-7_87 {
  width: 26.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 19.8019rem;
  justify-content: flex-start;
  align-items: center;
}

.Pixso-frame-7_88 {
  width: 4.625rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-1_112 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(32, 48, 68, 1);
  width: auto;
  height: auto;
  position: relative;
  flex-shrink: 0;
  white-space: pre;
  flex-grow: 0;
}

.Pixso-frame-7_90 {
  width: 2.5975rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(210, 228, 255, 1);
}

.Pixso-paragraph-7_91 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(74, 64, 224, 1);
  width: 1.5975rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_44 {
  width: 26.25rem;
  height: 17.5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
}

.Pixso-frame-6_45 {
  width: 26.25rem;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-6_46 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-6_47 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 13.1006rem;
  justify-content: flex-start;
}

.Pixso-paragraph-6_48 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 8.8994rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_49 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-paragraph-6_50 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(74, 44, 0, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_51 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-6_52 {
  width: 22.0994rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-6_53 {
  width: 25%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 75%;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-6_54 {
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-6_55 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4006rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-6_56 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(248, 160, 16, 1);
}

.Pixso-frame-6_57 {
  width: 26.25rem;
  height: 4.6875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  justify-content: center;
  padding: 1rem 1rem 1rem 1rem;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.Pixso-frame-6_58 {
  width: 24.25rem;
  height: 2.6875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.Pixso-frame-6_59 {
  width: 24.25rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 15rem;
  justify-content: flex-start;
}

.Pixso-paragraph-6_60 {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 1.25rem;
  color: rgba(32, 48, 68, 1);
  width: 7rem;
  height: 1.25rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_61 {
  width: 2.25rem;
  height: 1.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.125rem 0.5rem 0.125rem 0.5rem;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(105, 246, 184, 1);
}

.Pixso-paragraph-6_62 {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(0, 90, 60, 1);
  width: 1.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-frame-6_63 {
  width: 24.25rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.75rem;
  align-items: center;
}

.Pixso-frame-6_64 {
  width: 22.0594rem;
  height: 0.375rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
  background-color: rgba(220, 233, 255, 1);
}

.Pixso-rectangle-6_65 {
  width: 60%;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0%;
  right: 40%;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}

.Pixso-frame-6_66 {
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.Pixso-paragraph-6_67 {
  font-size: 0.625rem;
  font-family: "Inter-Semi Bold";
  font-weight: 700;
  display: flex;
  flex-direction: column;
  justify-content: center;
  line-height: 0.9375rem;
  color: rgba(104, 120, 143, 1);
  width: 1.4406rem;
  height: 0.9375rem;
  position: relative;
  flex-shrink: 0;
}

.Pixso-rectangle-6_68 {
  width: 0.25rem;
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  background-color: rgba(0, 105, 71, 1);
}
</style>
