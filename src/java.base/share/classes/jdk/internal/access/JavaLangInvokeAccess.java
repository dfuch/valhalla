/*
 * Copyright (c) 2015, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.internal.access;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public interface JavaLangInvokeAccess {
    /**
     * Create a new MemberName instance. Used by {@code StackFrameInfo}.
     */
    Object newMemberName();

    /**
     * Returns the name for the given MemberName. Used by {@code StackFrameInfo}.
     */
    String getName(Object mname);

    /**
     * Returns the {@code MethodType} for the given MemberName.
     * Used by {@code StackFrameInfo}.
     */
    MethodType getMethodType(Object mname);

    /**
     * Returns the descriptor for the given MemberName.
     * Used by {@code StackFrameInfo}.
     */
    String getMethodDescriptor(Object mname);

    /**
     * Returns {@code true} if the given MemberName is a native method.
     * Used by {@code StackFrameInfo}.
     */
    boolean isNative(Object mname);

    /**
     * Returns the declaring class for the given MemberName.
     * Used by {@code StackFrameInfo}.
     */
    Class<?> getDeclaringClass(Object mname);

    /**
     * Returns a map of class name in internal forms to its corresponding
     * class bytes per the given stream of LF_RESOLVE and SPECIES_RESOLVE
     * trace logs. Used by GenerateJLIClassesPlugin to enable generation
     * of such classes during the jlink phase.
     */
    Map<String, byte[]> generateHolderClasses(Stream<String> traces);

    /**
     * Returns a var handle view of a given memory address.
     * Used by {@code jdk.internal.foreign.LayoutPath} and
     * {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle memoryAccessVarHandle(Class<?> carrier, long alignmentMask,
                                    ByteOrder order, long offset, long[] strides);

    /**
     * Is {@code handle} a memory access varhandle?
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    boolean isMemoryAccessVarHandle(VarHandle handle);

    /**
     * Returns the carrier associated with a memory access var handle.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    Class<?> memoryAddressCarrier(VarHandle handle);

    /**
     * Returns the alignment mask associated with a memory access var handle.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    long memoryAddressAlignmentMask(VarHandle handle);

    /**
     * Returns the byte order associated with a memory access var handle.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    ByteOrder memoryAddressByteOrder(VarHandle handle);

    /**
     * Returns the offset associated with a memory access var handle.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    long memoryAddressOffset(VarHandle handle);

    /**
     * Returns the strides associated with a memory access var handle.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    long[] memoryAddressStrides(VarHandle handle);

    /**
     * Var handle carrier combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle filterValue(VarHandle target, MethodHandle filterToTarget, MethodHandle filterFromTarget);

    /**
     * Var handle filter coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle filterCoordinates(VarHandle target, int pos, MethodHandle... filters);

    /**
     * Var handle drop coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle dropCoordinates(VarHandle target, int pos, Class<?>... valueTypes);

    /**
     * Var handle permute coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle permuteCoordinates(VarHandle target, List<Class<?>> newCoordinates, int... reorder);

    /**
     * Var handle collect coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle collectCoordinates(VarHandle target, int pos, MethodHandle filter);

    /**
     * Var handle insert coordinates combinator.
     * Used by {@code jdk.incubator.foreign.MemoryHandles}.
     */
    VarHandle insertCoordinates(VarHandle target, int pos, Object... values);

    String inlineObjectToString(Object o);
}
